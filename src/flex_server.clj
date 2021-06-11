(ns flex-server
  (:require [reitit.ring :as ring]
            [ring.adapter.jetty :as jetty]))

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

(def web-app
  (ring/ring-handler
   (ring/router
    ["/is-scrambled"
     {:get {:handler (fn [{{{:keys [scrambled word]} :query} :parameters}]
                       {:status 200
                        :body {:scrambled? (scramble? scrambled word)}})}}])
   (ring/create-default-handler)))

(defn start-http-server [app]
  (jetty/run-jetty app {:port 3000 :join? false})
  (println "HTTP server is running"))

(defn -main [& args]
  (start-http-server #'web-app))
