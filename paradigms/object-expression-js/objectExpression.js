"use strict"

const operations = {};

function AbstractOperations(...args) {
    this.arg = args
}

AbstractOperations.prototype = {
    evaluate: function (...args) {
        return this.fnc(...this.arg.map(i => i.evaluate(...args)));
    },
    toString: function () {
        return this.arg.map(op => op.toString()).join(" ") + " " + this.ops;
    },
    diff: function (x) {
        return this.difff(x, ...this.arg);
    },
    prefix: function () {
        return "(" + this.ops + " " + this.arg.map(op => op.prefix()).join(" ") + ")";
    },
    postfix: function () {
        return "(" + this.arg.map(op => op.postfix()).join(" ") + " " + this.ops + ")";
    }
};

function Const_Variable(op) {
    this.op = op;
}

Const_Variable.prototype = {
    toString: function () {
        return this.op.toString();
    },
    prefix: function () {
        return this.op.toString();
    },
    postfix: function () {
        return this.op.toString();
    }
}

function ConstVariableFactory(evaluate, diff) {
    function Constructor(ops) {
        Const_Variable.call(this, ops);
    }

    Constructor.prototype = Object.create(Const_Variable.prototype);
    Constructor.prototype.evaluate = evaluate;
    Constructor.prototype.diff = diff;
    return Constructor;
}

const Const = ConstVariableFactory(
    function () {
        return this.op
    },
    function () {
        return Const.ZERO
    }
);
const Variable = ConstVariableFactory(
    function (...args) {
        return args[variables[this.op]]
    },
    function (x) {
        return x === this.op ? Const.ONE : Const.ZERO
    }
);

function OperationFactory(op, func, diff) {
    let Constructor = function (...args) {
        AbstractOperations.call(this, ...args);
    }
    Constructor.prototype = Object.create(AbstractOperations.prototype);
    Constructor.prototype.ops = op;
    Constructor.prototype.fnc = func;
    Constructor.prototype.difff = diff;
    operations[op] = [Constructor, func.length];
    return Constructor;
}

Const.THREE = new Const(3);
Const.ONE = new Const(1);
Const.ZERO = new Const(0);
Const.TWO = new Const(2);

const variables = {
    "x": 0,
    "y": 1,
    "z": 2
};

const Add = OperationFactory(
    "+",
    (op1, op2) => op1 + op2,
    (x, op1, op2) => new Add(op1.diff(x), op2.diff(x))
);

const Subtract = OperationFactory(
    "-",
    (op1, op2) => op1 - op2,
    (x, op1, op2) => new Subtract(op1.diff(x), op2.diff(x))
);

const Multiply = OperationFactory(
    "*",
    (op1, op2) => op1 * op2,
    (x, op1, op2) => new Add(new Multiply(op1.diff(x), op2), new Multiply(op2.diff(x), op1))
);

const Divide = OperationFactory(
    "/",
    (op1, op2) => op1 / op2,
    (x, op1, op2) => new Divide(new Subtract(new Multiply(op1.diff(x), op2), new Multiply(op1, op2.diff(x))), new Multiply(op2, op2))
);

const Negate = OperationFactory(
    "negate",
    (op) => -op,
    (x, op) => new Negate(op.diff(x))
);

const Cube = OperationFactory(
    "cube",
    (op) => Math.pow(op, 3),
    (x, op) => new Multiply(new Multiply(Const.THREE, new Multiply(op, op)), op.diff(x))
);

const Cbrt = OperationFactory(
    "cbrt",
    (op) => Math.cbrt(op),
    (x, op) => new Multiply(new Divide(Const.ONE, new Multiply(Const.THREE, new Cbrt(new Multiply(op, op)))), op.diff(x))
);

const Sumsq = OperationFactory(
    "sumsq",
    (...ops) => (ops.reduce((result, cur) => result + (cur * cur), 0)),
    (x, ...ops) => ops.length === 0 ? Const.ZERO : ops.reduce((result, cur) => new Add(result, new Multiply(new Multiply(Const.TWO, cur), cur.diff(x))), Const.ZERO)
);

const Length = OperationFactory(
    "length",
    (...ops) => Math.sqrt(ops.reduce((result, cur) => result + (cur * cur), 0)),
    (x, ...ops) => ops.length === 0 ? Const.ZERO : new Divide(new Sumsq(...ops).diff(x), new Multiply(Const.TWO, new Length(...ops)))
);

const parse = str => {
    let k = str.split(/\s+/);
    let st_ck = [];
    k.forEach(i => {
        if (i in operations) {
            st_ck.push(new operations[i][0](...st_ck.splice(-operations[i][1])));
        } else if (i in variables) {
            st_ck.push(new Variable(i));
        } else if (i !== "") {
            st_ck.push(new Const(parseInt(i)));
        }
    });
    return st_ck[0];
};

function ExceptionFactory() {
    function Constructor(message) {
        this.message = message;
    }

    Constructor.prototype = Object.create(Error.prototype);
    Constructor.prototype.constructor = Constructor;
    return Constructor;
}

const Exception = ExceptionFactory();

const parsePrefix = str => {
    let result = [];
    let current = [];
    let opers = false;
    let balance = 0;
    let iterator = 0;
    str = str.replaceAll(')', ' ) ');
    str = str.replaceAll('(', ' ( ');
    let k = str.split(" ").filter(e => e.trim().length > 0);
    if (k.length === 0) {
        throw new Exception("Empty input");
    }
    k.forEach(i => {
        if (i === "(") {
            balance++;
            result.push("(");
            opers = true;
        } else if (opers) {
            if (i in operations) {
                current.push(i);
            } else {
                throw new Exception("Unknown operation, error at index " + iterator);
            }
            opers = false;
        } else if (!isNaN(+i) || i in variables) {
            if (!isNaN(+i)) {
                result.push(new Const(+i));
            } else {
                result.push(new Variable(i));
            }
        } else if (i === ")") {
            balance--;
            let cnt = current.pop()
            if (operations[cnt][1] !== 0) {
                let arr = [];
                let c = result.pop();
                while (result.length !== 0 && c !== "(") {
                    arr.push(c);
                    c = result.pop();
                }
                if (operations[cnt][1] !== arr.length) {
                    throw new Exception("Not enough or too much operands, error at index " + iterator);
                }
                arr = arr.reverse();
                result.push(new operations[cnt][0](...arr));
            } else {
                let arr = [];
                let c = result.pop();
                while (result.length !== 0 && c !== "(") {
                    arr.push(c);
                    c = result.pop();
                }
                arr = arr.reverse();
                result.push(new operations[cnt][0](...arr));
            }
        } else {
            throw new Exception("Wrong expression, error at index " + iterator);
        }
        iterator++;
    });
    if (balance !== 0) {
        throw new Exception("Miss brackets");
    }
    if (current.length !== 0) {
        throw new Exception("Much operation")
    }
    if (result.length !== 1) {
        throw new Exception("Much operands");
    }
    return result[0];
};

const parsePostfix = str => {
    let result = [];
    let current = [];
    let operands = true;
    let balance = 0;
    let iterator = 0;
    str = str.replaceAll(')', ' ) ');
    str = str.replaceAll('(', ' ( ');
    let k = str.split(" ").filter(e => e.trim().length > 0);
    if (k.length === 0) {
        throw new Exception("Empty input");
    }
    k.forEach(i => {
        if (i === "(") {
            result.push('(');
            balance++;
            operands = true;
        } else if (operands) {
            if (!isNaN(+i)) {
                result.push(new Const(+i));
            } else if (i in variables) {
                result.push(new Variable(i));
            } else if (i in operations) {
                current.push(i);
                operands = false;
            } else {
                throw new Exception("New type of operand ans operations, error at index " + iterator);
            }
        } else if (i === ")") {
            operands = true;
            balance--;
            let cnt = current.pop()
            if (operations[cnt][1] !== 0) {
                let arr = [];
                let c = result.pop();
                while (result.length !== 0 && c !== "(") {
                    arr.push(c);
                    c = result.pop();
                }
                if (operations[cnt][1] !== arr.length) {
                    throw new Exception("Not enough or too much operands, error at index " + iterator);
                }
                arr = arr.reverse();
                result.push(new operations[cnt][0](...arr));
            } else {
                let arr = [];
                let c = result.pop();
                while (result.length !== 0 && c !== "(") {
                    arr.push(c);
                    c = result.pop();
                }
                arr = arr.reverse();
                result.push(new operations[cnt][0](...arr));
            }
        } else {
            throw new Exception("Wrong expression, error at index " + iterator);
        }
        iterator++;
    });
    if (balance !== 0) {
        throw new Exception("Miss brackets");
    }
    if (current.length !== 0) {
        throw new Exception("Much operation")
    }
    if (result.length !== 1) {
        throw new Exception("Much operands");
    }
    return result[0];
};