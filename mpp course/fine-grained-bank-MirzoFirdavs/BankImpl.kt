import java.util.concurrent.locks.ReentrantLock

/**
 * Bank implementation.
 *
 * @author : Zaynidinov Mirzofirdavs
 */
class BankImpl(n: Int) : Bank {
    private val accounts: Array<Account> = Array(n) { Account() }

    override val numberOfAccounts: Int
        get() = accounts.size

    override fun getAmount(index: Int): Long {
        accounts[index].lock.lock()

        val result: Long

        try {
            result = accounts[index].amount
        } finally {
            accounts[index].lock.unlock()
        }

        return result
    }

    override val totalAmount: Long
        get() {
            var result: Long = 0

            for (account in accounts) {
                account.lock.lock()
            }
            for (account in accounts) {
                result += account.amount
            }
            for (account in accounts) {
                account.lock.unlock()
            }
            return result
        }

    override fun deposit(index: Int, amount: Long): Long {
        require(amount > 0) {
            "Invalid amount: $amount"
        }

        val account = accounts[index]
        val result: Long

        account.lock.lock()

        try {
            check(!(amount > Bank.MAX_AMOUNT || account.amount + amount > Bank.MAX_AMOUNT)) {
                "Overflow"
            }

            account.amount += amount
            result = account.amount
        } finally {
            account.lock.unlock()
        }

        return result
    }

    override fun withdraw(index: Int, amount: Long): Long {
        require(amount > 0) {
            "Invalid amount: $amount"
        }

        val account = accounts[index]
        val result: Long

        account.lock.lock()

        try {
            check(account.amount - amount >= 0) {
                "Underflow"
            }

            account.amount -= amount
            result = account.amount
        } finally {
            account.lock.unlock()
        }

        return result
    }

    override fun transfer(fromIndex: Int, toIndex: Int, amount: Long) {
        require(amount > 0) {
            "Invalid amount: $amount"
        }
        require(fromIndex != toIndex) {
            "fromIndex == toIndex"
        }

        val from = accounts[fromIndex]
        val to = accounts[toIndex]

        if (fromIndex < toIndex) {
            from.lock.lock()
            to.lock.lock()
        } else {
            to.lock.lock()
            from.lock.lock()
        }

        try {
            check(amount <= from.amount) {
                "Underflow"
            }
            check(!(amount > Bank.MAX_AMOUNT || to.amount + amount > Bank.MAX_AMOUNT)) {
                "Overflow"
            }
            from.amount -= amount
            to.amount += amount
        } finally {
            from.lock.unlock()
            to.lock.unlock()
        }
    }

    class Account {
        val lock = ReentrantLock()
        var amount: Long = 0
    }
}