(ns kit.guestbook-with-db.web.xt.route
  (:require [integrant.core :as ig]
            [kit.guestbook-with-db.web.controllers.health :as health]))



;; Routes
(defn api-routes [_opts]
  [[""  {:get (fn [_req] {:status 200
                          :headers {"Content-Type" "text/html"}
                          :body "GET XT request"})
         :post (fn [_req] {:status 200 :body "POST request"})}]])


(derive :reitit.routes/xt :reitit/routes)

(defmethod ig/init-key :reitit.routes/xt
  [_ {:keys [base-path]
      :or   {base-path ""}
      :as   opts}]
  [base-path #_(route-data opts) (api-routes opts)])
