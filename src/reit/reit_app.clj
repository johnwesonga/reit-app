(ns reit.reit-app
  (:gen-class)
  (:require [integrant.core :as ig]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as resp]
            [reit.handler :as handler]))

 (def config
   (ig/read-string (slurp "resources/config.edn")))

; (def config
;   {:adapter/jetty {:handler (ig/ref :handler/run-app) :port 8080}
;    :handler/run-app {:db nil}})

(defmethod ig/init-key :adapter/jetty [_ {:keys [handler] :as opts}]
  (jetty/run-jetty handler (-> opts (dissoc :handler) (assoc :join? false))))

(defmethod ig/init-key :handler/run-app [_ {:keys [db]}]
  (handler/app db))

(defmethod ig/halt-key! :adapter/jetty [_ server]
  (.stop server))


(defn -main []
  (ig/init config))
