(ns flex-client
  (:require [ajax.core :refer [GET]]
            [reagent.core :as r]
            [reagent.dom :as rd]
            [schema.core :as s :include-macros true]))

(s/defrecord FormData
    [scrambled :- s/Str
     word :- s/Str
     answer :- s/Str])

(s/defrecord RequestData
    [scrambled :- s/Str
     word :- s/Str])

(letfn [(on-change [elem input-key atom-data]
          (let [elem-val (-> elem .-target .-value)
                {:keys [scrambled word]} (assoc @atom-data input-key elem-val)]
            (GET "/is-scrambled"
                 {:params (RequestData. scrambled word)
                  :handler #(reset! atom-data
                                    (FormData. scrambled
                                               word
                                               (str (:scrambled? %))))})))]
  (defn- mk-text-input [id-name atom-data]
    (let [input-key (keyword id-name)]
      [:input.form-control.mb-2.mr-sm-2
       {:type "text" :id id-name :name id-name :placeholder id-name
        :value (input-key @atom-data)
        :on-change #(on-change % input-key atom-data)}])))

(defn form-component []
  (let [atom-data (r/atom (FormData. "" "" ""))]
    (fn []
      (let [answer (:answer @atom-data)]
        [:div
         [:div.jumbotron>h1 "Scrambled?"]
         [:form.form-inline
          (mk-text-input "scrambled" atom-data)
          (mk-text-input "word" atom-data)]
         [:p "Is it scrambled? "
          [:span {:class (if (== answer "true") "text-success" "text-danger")}
           answer]]]))))

(defn init []
  (rd/render [form-component]
             (.getElementById js/document "primary-container")))

(init)
