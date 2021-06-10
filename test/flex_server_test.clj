(ns flex-server-test
  (:require [clojure.test :refer :all]
            [flex-server :refer [scramble?]]))

(deftest test-challenge-examples
  (is (true? (scramble? "rekqodlw" "world")))
  (is (true? (scramble? "cedewaraaossoqqyt" "codewars")))
  (is (false? (scramble? "katas" "steak"))))
