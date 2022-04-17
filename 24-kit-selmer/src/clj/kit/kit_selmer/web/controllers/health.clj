(ns kit.kit-selmer.web.controllers.health
  (:require [kit.kit-selmer.web.page.layout :as layout]
            [ring.util.http-response :as http-response])
  (:import [java.util Date]))

(defn healthcheck!
  [req]
  (http-response/ok
   {:time     (str (Date. (System/currentTimeMillis)))
    :up-since (str (Date. (.getStartTime (java.lang.management.ManagementFactory/getRuntimeMXBean))))
    :app      {:status  "up"
               :message ""}}))


(defn hello3 [{:keys [selmer] :as ctx}]
  (fn [req]
    (http-response/ok {:result (str req)})))


(defn hello [{:keys [selmer] :as ctx}]
  (fn [request]
    (layout/render request "hello.html")))