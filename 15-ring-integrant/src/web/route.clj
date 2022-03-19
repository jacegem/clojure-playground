(ns web.route
  (:require [muuntaja.core :as m]
            [reitit.ring :refer [ring-handler router]]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.parameters :as params]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.util.http-response :as hr]
            [web.rest.user.router.user-router :refer [user-router]]))


(defn default []
  ["/" {:get {:no-doc    true
              :summary   "health check"
              :responses {200 {:body {:message string?}}}
              :handler   (fn [_] (hr/ok {:message "Hello Web"}))}}])

(defn health []
  ["/health" {:get {:no-doc    true
                    :summary   "health check"
                    :responses {200 {:body {:message string?}}}
                    :handler   (fn [_] (hr/ok {:message "I'm alive!"}))}}])

(defn app [context]
  (ring-handler
   (router
    [(default)
     (health)
     (user-router context)]
    ;; router data affecting all routes
    {:data {:muuntaja   m/instance
            :middleware [params/parameters-middleware
                         muuntaja/format-middleware]}})
   {:middleware wrap-reload}))