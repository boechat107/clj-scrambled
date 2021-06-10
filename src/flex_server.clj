(ns flex-server)

(defn scramble? [scrambled word]
  ;; str -> str -> bool
  "Returns 'true' if 'word' can be arranged from 'scrambled'."
  ;; 1. First we count the available chars in "scrambled".
  ;; 2. For each char in "word", we update the previous count or
  ;; cancel the procedure if no more of char can be found.
  ;; 3. Canceled computation returns an empty map.
  ;; 4. Empty map means missing characters and the function returns "false".
  (let [counter (fn [freqs c]
                  (if (-> (get freqs c 0) dec neg?)
                    (reduced {})
                    (update freqs c dec)))]
    (->> word
         (reduce counter (frequencies scrambled))
         empty?
         not)))


(defn -main [& args]
  (println "hello" args))
