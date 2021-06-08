"use strict";

const operations = {};
const abstractOperation = f => (...args) => (...vars) => f(...args.map(arg => arg(...vars)));
const cnst = arg => () => arg;
const variable = arg => (...vars) => vars[variables[arg]];

function op (f, sign) {
    let operation = abstractOperation(f);
    operations[sign] = [operation, f.length];
    return operation;
}

const negate = op(arg => -arg, "negate");
const add = op((l, r) => l + r, "+");
const subtract = op((l, r) => l - r, "-");
const multiply = op((l, r) => l * r, "*");
const divide = op((l, r) => l / r, "/");
const max3 = op((l, m, r) => Math.max(l, m, r), "max3");
const min5 = op((a, b, c, d, e) => Math.min(a, b, c, d, e), "min5");

const one = cnst(1);
const two = cnst(2);

const constants = {
    "one": one,
    "two": two,
};

const variables = {
    "x": 0,
    "y": 1,
    "z": 2
}

const parse = expression => {
    let stack = [];
    const tokens = expression.trim().split(/\s+/);
    tokens.forEach(token => {
        if (token in operations) {
            stack.push(operations[token][0](...stack.splice(-operations[token][1])));
        } else if (token in variables) {
            stack.push(variable(token));
        } else if (token in constants) {
            stack.push(constants[token]);
        } else {
            stack.push(cnst(parseInt(token)));
        }
    });
    return stack[0];
}