(ns kit.guestbook.web.routes.b
  (:require [kit.guestbook.web.routes.api :refer [route-data]]
            [integrant.core :as ig]
            [ring.util.http-response :as http-response])
  (:import [java.util Date]))


;; Routes
(defn api-routes [_opts]
  [[""  {:get (fn [_req] {:status 200
                          :headers {"Content-Type" "text/html"}
                          :body "GET request"})
         :post (fn [_req] {:status 200 :body "POST request"})}]])

(derive :reitit.routes/b :reitit/routes)

(defmethod ig/init-key :reitit.routes/b
  [_ {:keys [base-path]
      :or   {base-path ""}
      :as   opts}]
  [base-path (route-data opts) (api-routes opts)])

