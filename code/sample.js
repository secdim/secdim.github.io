// Snippets from a fix for a race condition bug (TOCTOU)
// by Lukito Truong
class Amount {
    constructor(value) {
        const AMOUNT_LENGTH_MAX = 16; // Number.MAX_SAFE_INTEGER length as a string
        const AMOUNT_VALUE_MAX = Number.MAX_SAFE_INTEGER;
        const AMOUNT_VALUE_MIN = 0;

        // Short-circuit obviously wrong values before any parsing
        if (value === undefined ||
            value === null ||
            value === "" ||
            typeof value === 'string' && value.length > AMOUNT_LENGTH_MAX) {
            throw new TypeError('Invalid value');
        }

        const numberValue = Number(value);
        if (Number.isNaN(numberValue) ||
            numberValue < AMOUNT_VALUE_MIN || numberValue > AMOUNT_VALUE_MAX) {
            throw new TypeError('Invalid value');
        }

        this.value = numberValue;
        Object.freeze(this);
    }
}

module.exports = Amount

// Vulnerable part
//var transfer = (balance, amount) => {
//    var transfered = 0;
//    if (amount <= balance) {
//        balance = balance - amount;
//        transfered = transfered + parseInt(amount);
//        return { balance, transfered };
//    } else
//        return { undefined, undefined };
//};
