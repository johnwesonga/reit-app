(ns reit.handler
(:require [reitit.ring :as ring]
          [reit.controller :as ctl]
          [reitit.ring.middleware.parameters :as parameters]
          [ring.middleware.keyword-params :refer [wrap-keyword-params]]))

(defn app 
 [db]
    (ring/ring-handler
        (ring/router
            [["/" {
                 :handler #'ctl/default}]
             ["/greet/:name" {:get {:parameters {:path {:name string?}}}
                                :handler #'ctl/greet}]]
            {:data {:middleware [;; query-params & form-params
                                parameters/parameters-middleware
                                wrap-keyword-params]}})
        (ring/routes
            (ring/create-resource-handler
                {:path "/"})
           (ring/create-resource-handler
            {:not-found (constantly {:status 404 :body "Not found"})}))))