(ns reit.handler
(:require [reitit.ring :as ring]
          [reit.controller :as ctl]
          [reitit.coercion.spec]
          [reitit.coercion.schema]
          [schema.core :as s]
          [reitit.ring.middleware.parameters :as parameters]
          [ring.middleware.keyword-params :refer [wrap-keyword-params]]))

(defn app 
 [db]
    (ring/ring-handler
        (ring/router
            [["/healthz" {:get (fn [_] {:status 200 :body "healthy"})}]
            ["/" {
                 :handler #'ctl/default}]
             ["/greet/:name" {:get {:parameters {:path {:name s/Str}}}
                                :handler #'ctl/greet}]]
            {:data {:coercion reitit.coercion.schema/coercion
                    :middleware [;; query-params & form-params
                                parameters/parameters-middleware
                                wrap-keyword-params]}})
        (ring/routes
            (ring/create-resource-handler
                {:path "/"})
           (ring/create-default-handler
            {:not-found (constantly {:status 404 :body "Not found"})}))))