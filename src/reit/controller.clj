(ns reit.controller
    (:require [ring.util.response :as resp]
              [cljstache.core :refer [render-resource]]))

(defn render-helper
 [view data]
    (render-resource (str "public/" view ".moustache") data))

(defn default
 [req]
  (resp/response 
    (render-resource "public/index.moustache")))

(defn greet
 [req]
  (let [name (get-in req [:path-params :name])]
      (resp/response 
        (render-resource "public/greet.moustache" {:name name}))))