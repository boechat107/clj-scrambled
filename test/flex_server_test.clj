(ns flex-server-test
  (:require [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]
            [cheshire.core :refer [parse-string parse-stream]]
            [ring.mock.request :as mock]
            [flex-server :refer [scramble? web-app]]))

(defn scramble [word]
  (-> (seq word) shuffle clojure.string/join))

(defn mk-req
  "Optional[map] -> Response map
  Makes a request to flex-server's handler and returns its response.
  If the body contains JSON, the function parses the content before returning
  the response."
  ([]
   (mk-req nil))
  ([params]
   (let [resp (web-app (mock/request :get "/is-scrambled" params))]
     (if (re-find #"application/json" (get-in resp [:headers "Content-Type"]))
       (update resp :body #(with-open [s (clojure.java.io/reader %)]
                             (parse-stream s true)))
       resp))))

(defn mk-valid-req
  "str, str -> Response map"
  [scrambled word]
  (mk-req {"scrambled" scrambled "word" word}))


(testing "Only the primary function of the system"
 (deftest test-simplistic-examples
   (is (false? (scramble? "recogized" "recognized")))
   (is (true? (scramble? "recognnized" "recognized"))))

 (deftest test-shuffles
   (let [samples (remove empty? (gen/sample gen/string-alphanumeric))]
     (is (every? #(scramble? (scramble %) %) samples)))))

(testing "The system using its web API"
  (deftest test-challenge-examples-with-handler
    (letfn [(check [scrambled word bool-fn]
              (let [resp (mk-valid-req scrambled word)]
                (is (== 200 (:status resp)))
                (is (bool-fn (get-in resp [:body :scrambled?])))))]
      (check "rekqodlw" "world" true?)
      (check "cedewaraaossoqqyt" "codewars" true?)
      (check "katas" "steak" false?)))

  (deftest test-nonexistent-route
    (let [resp (web-app (mock/request :get "/anything"))]
      (is (== 404 (:status resp)))))

  (deftest test-nonexistent-verb
    (let [resp (web-app (mock/request :post "/is-scrambled"))]
      (is (== 405 (:status resp)))))

  (deftest test-invalid-requests
    ;; Missing parameters.
    (is (== 400 (:status (mk-req))))))
