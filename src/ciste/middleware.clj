(ns ciste.middleware
  (:require [clojure.pprint :as p]))

(defn wrap-http-serialization
  [handler]
  (fn [request]
    (->> request
         (merge {:serialization :http, :format :html})
         handler)))

(defn with-request-logging
  "Log each request"
  [handler]
  (fn [request]
    (p/pprint request)
    (handler request)))

(defn apply-wrappers
  "wraps the action with middleware contained in wrappers"
  [action wrappers]
  (if wrappers
    (reduce #(%2 %1) (apply vector action wrappers))
    action))

