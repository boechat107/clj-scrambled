(ns flex-server
  (:require [muuntaja.core :as m]
            [reitit.coercion.schema]
            [reitit.ring :as ring]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.parameters :as parameters]
            [ring.adapter.jetty :as jetty]
            [schema.core :as s]))

(defn scramble?
  "str, str -> bool
  Returns 'true' if 'word' can be arranged from 'scrambled'."
  [scrambled word]
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
     {:get {:parameters {:query {:scrambled s/Str :word s/Str}}
            :handler (fn [{{:strs [scrambled word]} :query-params}]
                       {:status 200
                        :body {:scrambled? (scramble? scrambled word)}})}}]
    ;; Middleware configuration for this router.
    {:data {:coercion reitit.coercion.schema/coercion
            :muuntaja m/instance
            :middleware [;; Content negotiation, request/response formatting.
                         muuntaja/format-middleware
                         ;; Parses URL encoded query parameters.
                         parameters/parameters-middleware
                         ;; Converts exceptions into nice responses.
                         rrc/coerce-exceptions-middleware
                         ;; Validates and coerces the request.
                         rrc/coerce-request-middleware]}})
   (ring/routes
    (ring/create-file-handler {:root "resources/public" :path "/"})
    (ring/create-default-handler))))

(defn start-http-server [app]
  (jetty/run-jetty app {:port 3000 :join? false})
  (println "HTTP server is running"))

(defn -main [& args]
  (start-http-server #'web-app))
