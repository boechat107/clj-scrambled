(ns flex-server-test
  (:require [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]
            [cheshire.core :refer [parse-string parse-stream]]
            [ring.mock.request :as mock]
            [flex-server :refer [scramble? web-app]]))

(defn scramble [word]
  (-> (seq word) shuffle clojure.string/join))

(defn mk-req
  "str, str -> Response map
  Makes a mocked request to flex-server's handler."
  [scrambled word]
  (let [resp (->> {"scrambled" scrambled "word" word}
                  (mock/request :get "/is-scrambled")
                  web-app)]
    (if (re-find #"application/json" (get-in resp [:headers "Content-Type"]))
      (update resp :body #(parse-stream (clojure.java.io/reader %) true))
      resp)))


(deftest test-simplistic-examples
  (is (false? (scramble? "recogized" "recognized")))
  (is (true? (scramble? "recognnized" "recognized"))))

(deftest test-shuffles
  (let [samples (remove empty? (gen/sample gen/string-alphanumeric))]
    (is (every? #(scramble? (scramble %) %) samples))))

(deftest test-challenge-examples-with-handler
  (letfn [(check [scrambled word bool-fn]
            (let [resp (mk-req scrambled word)]
              (is (== 200 (:status resp)))
              (is (bool-fn (get-in resp [:body :scrambled?])))))]
    (check "rekqodlw" "world" true?)
    (check "cedewaraaossoqqyt" "codewars" true?)
    (check "katas" "steak" false?)))
