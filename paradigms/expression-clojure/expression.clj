(defn abstractOperationFunction [op] (fn [& ops] (fn [args] (apply op (map (fn [i] (i args)) ops)))))

(defn constant [value] (fn [_] value))

(defn variable [name] (fn [args] (get args name)))

(defn evaluate [expr map] (expr map))

(def add (abstractOperationFunction +))

(def subtract (abstractOperationFunction -))

(def multiply (abstractOperationFunction *))

(def negate (abstractOperationFunction -))

(defn op-divide
  ([op] (/ 1 (double op)))
  ([op & args] (/ op (double (apply * args)))))

(def divide (abstractOperationFunction op-divide))

(def avg (abstractOperationFunction (fn [& args] (/ (apply + args) (count args)))))

(def sum (abstractOperationFunction (fn [& args] (apply + args))))

(def operationsFunctions {'+ add, '- subtract, '* multiply, '/ divide, 'negate negate, 'sum sum, 'avg avg})

(defn parseExpressionFunction [string]
  (cond
    (number? string) (constant string)
    (symbol? string) (variable (str string))
    (list? string) (apply (operationsFunctions (first string)) (map parseExpressionFunction (rest string)))))

(defn parseFunction [string] (parseExpressionFunction (read-string string)))

;=============================================================================================================================================================================

(defn p_get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :prototype) (p_get (obj :prototype) key)
    :else nil))

(defn p_call [obj key & args]
  (apply (p_get obj key) obj args))

(defn field [key]
  #(p_get % key))

(defn method [key]
  (fn [this & args] (apply p_call this key args)))

(def toString (method :toString))
(def evaluate (method :evaluate))
(def diff (method :diff))
(def ops (field :operands))

(declare ZERO)

(def ConstantPrototype
  (let [value (field :value)]
    {:evaluate (fn [this _] (value this))
     :toString (fn [this] (format "%.1f" (double (value this))))
     :diff     (fn [_ _] ZERO)}))

(defn Constant [value]
  {:prototype ConstantPrototype
   :value     value})

(def ZERO (Constant 0))
(def ONE (Constant 1))

(def VariablePrototype
  (let [name (field :value)]
    {:toString #(name %1)
     :evaluate #(%2 (name %1))
     :diff     #(if (= (name %1) %2) ONE ZERO)}))

(defn Variable [name]
  {:prototype VariablePrototype
   :value     name})

(def OperationPrototype
  (let [operands (field :operands)
        functions (field :function)
        function (field :name)
        di_ff (method :di_f)]
    {:toString #(str "(" (functions %) " " (clojure.string/join " " (mapv toString (operands %))) ")")
     :evaluate #(apply (function %1) (mapv (fn [operand] (evaluate operand %2)) (operands %1)))
     :diff     #(di_ff %1 %2)}))

(defn OperationFactory
  [function name di_f]
  (fn [& operands]
    {:prototype {:prototype OperationPrototype
                 :function  function
                 :name      name
                 :di_f      (fn [this arg] (di_f (map #(diff % arg) (ops this)) (vec operands)))}
     :operands  (vec operands)}))

(def Add (OperationFactory '+ + (fn [diff_args _] (apply Add diff_args))))

(def Subtract (OperationFactory '- - (fn [diff_args _] (apply Subtract diff_args))))

(def Negate (OperationFactory 'negate - (fn [diff_args _] (apply Negate diff_args))))

(declare Multiply)

(defn multiply-of-diff [diff-args args]
  (let [expr (mapv vector args diff-args)]
    (second (reduce (fn [[op1' op2] [op1 op2']] [(Multiply op1 op1') (Add (Multiply op1 op2) (Multiply op2' op1'))]) expr))))

(def Multiply (OperationFactory '* * multiply-of-diff))

(def Square (fn [arg] (Multiply arg arg)))

(declare Divide)

(def expr (fn [x y] (Divide (Negate x) (Square y))))

(def Divide (OperationFactory '/ op-divide (fn [[diff_arg & diff-args] [arg & args]]
                                             (if (== (count args) 0)
                                               (expr diff_arg arg)
                                               (let [all_mul (apply Multiply args) diff_rule (multiply-of-diff diff-args args)]
                                                 (Divide (Subtract (Multiply all_mul diff_arg) (Multiply diff_rule arg)) (Square all_mul)))))))

(def Sum (OperationFactory 'sum + (fn [diff_args _] (apply Add diff_args))))

(def Avg (OperationFactory 'avg #(/ (apply + %&) (count %&)) (fn [diff_args _] (Divide (apply Add diff_args) (Constant (count diff_args))))))

(def operationsObject {'+ Add, '- Subtract, '*, Multiply, '/, Divide, 'negate Negate, 'sum Sum, 'avg Avg})

(defn parseExpressionObject [string]
  (cond
    (number? string) (Constant string)
    (symbol? string) (Variable (str string))
    (list? string) (apply (operationsObject (first string)) (map parseExpressionObject (rest string)))))

(defn parseObject [string] (parseExpressionObject (read-string string)))