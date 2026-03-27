<template>
  <div class="auth-page">
    <div class="auth-card">
      <div class="auth-logo">
        <svg width="36" height="36" viewBox="0 0 36 36" fill="none">
          <rect width="36" height="36" rx="10" fill="#4f46e5"/>
          <path d="M10 24L18 12L26 24H10Z" fill="white" opacity="0.9"/>
          <circle cx="18" cy="22" r="4" fill="white"/>
        </svg>
        <span>学生管理系统</span>
      </div>
      <h2>欢迎回来</h2>
      <p class="subtitle">请输入账号和密码登录</p>

      <form @submit.prevent="handleLogin">
        <div class="field">
          <label>账号</label>
          <div class="input-wrap">
            <svg class="input-icon" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clip-rule="evenodd"/>
            </svg>
            <input v-model="form.username" placeholder="请输入用户名" required autocomplete="username" />
          </div>
        </div>
        <div class="field">
          <label>密码</label>
          <div class="input-wrap">
            <svg class="input-icon" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd" d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z" clip-rule="evenodd"/>
            </svg>
            <input v-model="form.password" type="password" placeholder="请输入密码" required autocomplete="current-password" />
          </div>
        </div>
        <div v-if="errMsg" class="error-msg">{{ errMsg }}</div>
        <button type="submit" class="btn-primary" :disabled="loading">
          <span v-if="loading" class="spinner"></span>
          {{ loading ? '登录中...' : '登 录' }}
        </button>
      </form>

      <p class="switch-link">没有账号？<router-link to="/register">立即注册</router-link></p>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { authAPI } from '../api'

const router = useRouter()
const form = reactive({ username: '', password: '' })
const loading = ref(false)
const errMsg = ref('')

const handleLogin = async () => {
  errMsg.value = ''
  loading.value = true
  try {
    const { data } = await authAPI.login(form)
    localStorage.setItem('token', data.token || data)
    router.push('/students')
  } catch (err) {
    errMsg.value = err.message || '账号或密码错误'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.auth-card {
  background: #fff;
  border-radius: var(--radius-lg);
  padding: 40px 36px;
  width: 100%;
  max-width: 400px;
  box-shadow: var(--shadow-lg);
}

.auth-logo {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 28px;
}
.auth-logo span {
  font-size: 16px;
  font-weight: 600;
  color: var(--text);
}

h2 {
  font-size: 22px;
  font-weight: 700;
  color: var(--text);
  margin-bottom: 4px;
}
.subtitle {
  font-size: 13px;
  color: var(--text-muted);
  margin-bottom: 28px;
}

.field {
  margin-bottom: 16px;
}
.field label {
  display: block;
  font-size: 13px;
  font-weight: 500;
  color: var(--text);
  margin-bottom: 6px;
}
.input-wrap {
  position: relative;
}
.input-icon {
  position: absolute;
  left: 12px;
  top: 50%;
  transform: translateY(-50%);
  width: 16px;
  height: 16px;
  color: var(--text-light);
  pointer-events: none;
}
.input-wrap input {
  width: 100%;
  padding: 10px 12px 10px 36px;
  border: 1.5px solid var(--border);
  border-radius: var(--radius-sm);
  font-size: 14px;
  color: var(--text);
  background: #fff;
  transition: border-color 0.2s, box-shadow 0.2s;
  outline: none;
}
.input-wrap input:focus {
  border-color: var(--primary);
  box-shadow: 0 0 0 3px rgba(79,70,229,.1);
}
.input-wrap input::placeholder { color: var(--text-light); }

.error-msg {
  background: var(--danger-light);
  color: var(--danger);
  border-radius: var(--radius-sm);
  padding: 10px 12px;
  font-size: 13px;
  margin-bottom: 14px;
}

.btn-primary {
  width: 100%;
  padding: 11px;
  background: var(--primary);
  color: #fff;
  border: none;
  border-radius: var(--radius-sm);
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.2s, transform 0.1s;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  margin-top: 8px;
}
.btn-primary:hover:not(:disabled) { background: var(--primary-hover); }
.btn-primary:active:not(:disabled) { transform: scale(0.99); }
.btn-primary:disabled { opacity: 0.65; cursor: not-allowed; }

.spinner {
  width: 14px;
  height: 14px;
  border: 2px solid rgba(255,255,255,.4);
  border-top-color: #fff;
  border-radius: 50%;
  animation: spin 0.7s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }

.switch-link {
  text-align: center;
  font-size: 13px;
  color: var(--text-muted);
  margin-top: 20px;
}
</style>
