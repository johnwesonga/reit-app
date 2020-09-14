(ns reit.handler
(:require [reit.controller :as ctl]
          [reitit.ring :as ring]
          [reitit.ring.coercion :as coercion]
          [reitit.coercion.spec]
          [reitit.swagger :as swagger]
          [reitit.swagger-ui :as swagger-ui]
          [reitit.dev.pretty :as pretty]
          [reitit.ring.middleware.muuntaja :as muuntaja]
          [reitit.ring.middleware.parameters :as parameters]
          [muuntaja.core :as m]
          [ring.middleware.keyword-params :refer [wrap-keyword-params]]))

(defn app 
 [db]
    (ring/ring-handler
        (ring/router
            [
            ; ["/healthz" {:get (fn [_] {:status 200 :body "healthy"})}]
            ;  ["/" {
            ;      :handler #'ctl/default}]
            ;  ["/greet/:name" {:get {:parameters {:path {:name s/Str}}}
            ;                     :handler #'ctl/greet}]
             ["/api"
                  {:swagger {:tags ["api"]}}
                ["/users" {:get {
                                :summary "List all users"
                                :responses {200 {:body [{:username string?}]}}
                                :handler (fn [_] {:status 200 :content-type "application/json" :body [{:username "john"}{:username "peter"}]})}}]
                ["/user" {:get {
                               :summary "Get user"
                               :responses {200 {:body string?}}
                               :parameters {:query {:username string?}}
                               :handler (fn [{{{:keys [username]} :query} :parameters}] {:status 200 :body (str "username: " username)})}}]]
             ["/swagger.json"
                {:get {:no-doc true
                    :swagger {:info {:title "my-api"
                                        :description "with reitit-ring"}}
                    :handler (swagger/create-swagger-handler)}}]]
            {:exception pretty/exception
             :data {:coercion reitit.coercion.spec/coercion
                     :muuntaja m/instance
                    :middleware [;; query-params & form-params
                                parameters/parameters-middleware
                                swagger/swagger-feature
                                ;; content-negotiation
                                muuntaja/format-negotiate-middleware
                                ;; encoding response body
                                muuntaja/format-response-middleware
                                coercion/coerce-request-middleware
                                wrap-keyword-params]}})
        (ring/routes
            (ring/create-resource-handler
                {:path "/"})
            (swagger-ui/create-swagger-ui-handler
                {:path "/api"
                :config {:validatorUrl nil
                        :operationsSorter "alpha"}})
           (ring/create-default-handler
            {:not-found (constantly {:status 404 :body "Not found"})}))))