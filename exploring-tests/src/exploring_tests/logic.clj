(ns exploring-tests.logic)

(defn fits-in-queue?
  [hospital department]
  (some-> hospital
          department
          count
          (< 5)))

(defn fits-in-queue?
  [hospital department]
  (when-let [queue (get hospital department)]
    (-> queue
        count
        (< 5))
    ))

(defn arrived-at
  [hospital department patient]
  (if (fits-in-queue? hospital department)
    (update hospital department conj patient)
    (throw (ex-info "This department is full or doesn't exists" {:patient patient :type :impossible-to-add-patient-to-the-queue})))
  )

#_(defn- tries-to-add-to-the-queue
  [hospital department patient]
  (if (fits-in-queue? hospital department)
    (update hospital department conj patient)))

#_(defn arrived-at
  [hospital department patient]
  (if-let [new-hospital (tries-to-add-to-the-queue hospital department patient)]
    {:hospital new-hospital, :result :success}
    {:hospital hospital, :result :impossible-to-add-patient-to-the-queue}))

(defn was-attended-to
  [hospital department]
  (update hospital department pop))

(defn next-patient
  [hospital department]
  (-> hospital
      department
      peek))

(defn transfer
  [hospital from to]
  (let [patient (next-patient hospital from)]
    (-> hospital
        (was-attended-to from)
        (arrived-at to patient))))