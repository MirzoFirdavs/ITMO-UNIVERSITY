class BankAccount(amount: Int) {
    private var _balance: Int = amount
        private set(value) {
            logTransaction(field, value)
            field = value
        }

    val balance: Int
        get() = _balance

    init {
        require(amount >= 0) { "Bank account can't have a negative balance" }
    }

    fun deposit(amount: Int) {
        require(amount > 0) { "Should be impossible to deposit a negative or zero amount" }
        _balance += amount
    }

    fun withdraw(amount: Int) {
        require(amount > 0) { "Should be impossible to withdraw a negative or zero amount" }
        require(_balance >= amount) { "Should be impossible to withdraw amount greater than current balance" }
        _balance -= amount
    }
}

fun logTransaction(from: Int, to: Int) {
    println("$from -> $to")
}
