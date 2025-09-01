package com.example.bytewisdom.auth

import android.content.SharedPreferences
import com.example.bytewisdom.data.local.User
import com.example.bytewisdom.data.local.UserDao
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import android.util.Base64
import java.util.UUID

private const val ITER = 120_000
private const val KEY_LEN = 256 // bits
private const val PREF_SESSION = "session_username"

class LocalAuth(
    private val prefs: SharedPreferences,
    private val userDao: UserDao
) {
    suspend fun register(username: String, password: String): Result<Unit> {
        if (userDao.findByUsername(username) != null) {
            return Result.failure(IllegalStateException("Username already exists"))
        }
        val salt = genSalt()
        val hash = hash(password.toCharArray(), salt)
        userDao.insert(User(username = username, passwordHash = hash, salt = enc(salt)))
        setSession(username)
        return Result.success(Unit)
    }

    suspend fun login(username: String, password: String): Result<Unit> {
        val u = userDao.findByUsername(username) ?: return Result.failure(IllegalArgumentException("No such user"))
        val ok = hash(password.toCharArray(), dec(u.salt)) == u.passwordHash
        return if (ok) {
            setSession(username); Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Invalid credentials"))
        }
    }

    fun logout() { prefs.edit().remove(PREF_SESSION).apply() }
    fun isLoggedIn(): Boolean = prefs.getString(PREF_SESSION, null) != null
    fun currentUsername(): String? = prefs.getString(PREF_SESSION, null)

    // helpers
    private fun setSession(username: String) { prefs.edit().putString(PREF_SESSION, username).apply() }
    private fun genSalt(): ByteArray = ByteArray(16).also { SecureRandom().nextBytes(it) }
    private fun hash(password: CharArray, salt: ByteArray): String {
        val spec = PBEKeySpec(password, salt, ITER, KEY_LEN)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val bytes = skf.generateSecret(spec).encoded
        return enc(bytes)
    }
    private fun enc(b: ByteArray) = Base64.encodeToString(b, Base64.NO_WRAP)
    private fun dec(s: String) = Base64.decode(s, Base64.NO_WRAP)
}
