(ns flex-server-test
  (:require [clojure.test :refer :all]
            [clojure.test.check.generators :as gen]
            [flex-server :refer [scramble?]]))

(defn scramble [word]
  (-> (seq word) shuffle clojure.string/join))

(deftest test-challenge-examples
  (is (true? (scramble? "rekqodlw" "world")))
  (is (true? (scramble? "cedewaraaossoqqyt" "codewars")))
  (is (false? (scramble? "katas" "steak"))))

(deftest test-simplistic-examples
  (is (false? (scramble? "recogized" "recognized")))
  (is (true? (scramble? "recognnized" "recognized"))))

(deftest test-shuffles
  (let [samples (remove empty? (gen/sample gen/string-alphanumeric))]
    (is (every? #(scramble? (scramble %) %) samples))))
