(ns record-protocol-multimethod.logic
  (:require [record-protocol-multimethod.model :as r.model]))

(defn this-moment
  []
  (r.model/to-ms (java.util.Date.)))