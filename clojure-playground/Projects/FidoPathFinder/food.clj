(ns food
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

;; Parse the map file into a 2D vector of characters
(defn- parse-map [s]
  (vec (map vec (str/split-lines s))))

;; Validate the map by checking its structure and characters
(defn is-valid-map [m]
  (let [rows (count m)
        cols (count (first m))
        valid-chars #{\- \# \@}]
    (and
     ;; Ensure the map has at least one row and one column
     (> rows 0) (> cols 0)
     ;; Ensure all rows have the same number of columns
     (every? #(= cols (count %)) m)
     ;; Ensure all characters are valid
     (every? valid-chars (apply concat m)))))

;; Print the map row by row
(defn print-map [m]
  (doseq [row m] (println (apply str row)))
  (println))

;; Read the map file and return its content as a 2D vector
(defn read-map-file [file-name]
  (if (.exists (io/file file-name))
    ;; If the file exists, parse its content into a map
    (let [m (parse-map (slurp file-name))]
      [true m])
    ;; If the file does not exist, return false with nil
    [false nil]))

;; Check if a position is valid (within bounds and navigable)
(defn- is-valid-pos [m [x y]]
  (let [v (get-in m [y x] nil)]
    ;; The position must exist and be either '-' or '@'
    (and v (#{\- \@} v))))

;; Recursive function to find a path to the food ('@')
(defn- find-path-recur [m [x y]]
  (if (= \@ (get-in m [y x]))
    ;; If the current position is '@', the food is found
    [true m]
    (let [neighbors [[x (inc y)] [(inc x) y] [x (dec y)] [(dec x) y]] ; Adjacent positions
          m' (assoc-in m [y x] \+)] ; Mark the current position as visited ('+')
      (loop [ns neighbors m m']
        (if (empty? ns)
          ;; If no valid neighbors, mark this position as a dead end ('!')
          [false (assoc-in m [y x] \!)]
          (let [[nx ny] (first ns)] ; Take the next neighbor
            (if (is-valid-pos m [nx ny]) ; Check if the neighbor is valid
              (let [[found new-m] (find-path-recur m [nx ny])]
                (if found
                  ;; If the food is found, return immediately
                  [true new-m]
                  ;; Otherwise, continue checking other neighbors
                  (recur (rest ns) new-m)))
              ;; If the neighbor is invalid, skip to the next
              (recur (rest ns) m))))))))

;; Entry point for pathfinding; starts at the top-left corner (0, 0)
(defn find-path [m]
  (find-path-recur m [0 0]))
