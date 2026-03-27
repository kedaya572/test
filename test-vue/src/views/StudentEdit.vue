<template>
  <div class="page">
    <header class="topbar">
      <div class="topbar-left">
        <svg width="28" height="28" viewBox="0 0 36 36" fill="none">
          <rect width="36" height="36" rx="10" fill="#4f46e5"/>
          <path d="M10 24L18 12L26 24H10Z" fill="white" opacity="0.9"/>
          <circle cx="18" cy="22" r="4" fill="white"/>
        </svg>
        <span class="site-name">学生管理系统</span>
      </div>
      <button class="btn-back" @click="goBack">
        <svg viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M9.707 16.707a1 1 0 01-1.414 0l-6-6a1 1 0 010-1.414l6-6a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l4.293 4.293a1 1 0 010 1.414z" clip-rule="evenodd"/>
        </svg>
        返回列表
      </button>
    </header>

    <main class="main">
      <div class="page-header">
        <div class="breadcrumb">
          <span @click="goBack" class="crumb-link">学生列表</span>
          <svg viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd"/></svg>
          <span>{{ isEdit ? '编辑学生' : '新增学生' }}</span>
        </div>
        <h1>{{ isEdit ? '编辑学生信息' : '新增学生' }}</h1>
      </div>

      <div class="card">
        <form @submit.prevent="handleSubmit">
          <div class="section-title">基本信息</div>
          <div class="form-grid">
            <div class="field">
              <label>学号 <span class="required">*</span></label>
              <input v-model="form.studentNo" placeholder="请输入学号" required />
            </div>
            <div class="field">
              <label>姓名 <span class="required">*</span></label>
              <input v-model="form.name" placeholder="请输入姓名" required />
            </div>
            <div class="field">
              <label>性别</label>
              <select v-model="form.gender">
                <option value="">请选择性别</option>
                <option value="男">男</option>
                <option value="女">女</option>
              </select>
            </div>
            <div class="field">
              <label>年龄</label>
              <input v-model.number="form.age" type="number" placeholder="请输入年龄" min="1" max="150" />
            </div>
            <div class="field">
              <label>专业</label>
              <input v-model="form.major" placeholder="请输入专业" />
            </div>
          </div>

          <div class="section-title" style="margin-top: 28px;">联系方式</div>
          <div class="form-grid">
            <div class="field">
              <label>邮箱</label>
              <input v-model="form.email" type="email" placeholder="请输入邮箱" />
            </div>
            <div class="field">
              <label>手机号</label>
              <input v-model="form.phone" placeholder="请输入手机号" />
            </div>
          </div>

          <div v-if="errMsg" class="error-msg">{{ errMsg }}</div>

          <div class="form-actions">
            <button type="button" class="btn-cancel" @click="goBack">取消</button>
            <button type="submit" class="btn-submit" :disabled="loading">
              <span v-if="loading" class="spinner"></span>
              {{ loading ? '保存中...' : '保存' }}
            </button>
          </div>
        </form>
      </div>
    </main>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { studentAPI } from '../api'

const router = useRouter()
const route = useRoute()
const isEdit = ref(false)
const loading = ref(false)
const errMsg = ref('')
const form = reactive({ studentNo: '', name: '', gender: '', age: '', major: '', email: '', phone: '' })

onMounted(async () => {
  if (route.params.id) {
    isEdit.value = true
    try {
      const res = await studentAPI.get(route.params.id)
      Object.assign(form, res.data)
    } catch {
      errMsg.value = '加载学生信息失败'
    }
  }
})

const handleSubmit = async () => {
  errMsg.value = ''
  loading.value = true
  try {
    if (isEdit.value) {
      await studentAPI.update(route.params.id, form)
    } else {
      await studentAPI.create(form)
    }
    router.push('/students')
  } catch (err) {
    errMsg.value = err.message || '保存失败，请重试'
  } finally {
    loading.value = false
  }
}

const goBack = () => router.push('/students')
</script>

<style scoped>
.page { min-height: 100vh; background: var(--bg); }

.topbar {
  display: flex; align-items: center; justify-content: space-between;
  padding: 0 32px; height: 60px; background: #fff;
  border-bottom: 1px solid var(--border); box-shadow: var(--shadow-sm);
  position: sticky; top: 0; z-index: 100;
}
.topbar-left { display: flex; align-items: center; gap: 10px; }
.site-name { font-size: 15px; font-weight: 600; color: var(--text); }
.btn-back {
  display: flex; align-items: center; gap: 6px;
  padding: 7px 14px; border: 1.5px solid var(--border);
  border-radius: var(--radius-sm); background: #fff;
  font-size: 13px; color: var(--text-muted); cursor: pointer; transition: all 0.2s;
}
.btn-back svg { width: 15px; height: 15px; }
.btn-back:hover { border-color: var(--primary); color: var(--primary); background: var(--primary-light); }

.main { max-width: 800px; margin: 0 auto; padding: 32px 24px; }

.breadcrumb {
  display: flex; align-items: center; gap: 6px;
  font-size: 13px; color: var(--text-muted); margin-bottom: 8px;
}
.breadcrumb svg { width: 14px; height: 14px; color: var(--text-light); }
.crumb-link { cursor: pointer; }
.crumb-link:hover { color: var(--primary); }
h1 { font-size: 20px; font-weight: 700; color: var(--text); }
.page-header { margin-bottom: 24px; }

.card {
  background: #fff; border-radius: var(--radius);
  box-shadow: var(--shadow-sm); border: 1px solid var(--border);
  padding: 32px;
}

.section-title {
  font-size: 13px; font-weight: 600;
  color: var(--text-muted); text-transform: uppercase; letter-spacing: .5px;
  margin-bottom: 16px; padding-bottom: 10px;
  border-bottom: 1px solid var(--border);
}

.form-grid {
  display: grid; grid-template-columns: 1fr 1fr; gap: 16px 24px;
}
@media (max-width: 600px) { .form-grid { grid-template-columns: 1fr; } }

.field label {
  display: block; font-size: 13px; font-weight: 500;
  color: var(--text); margin-bottom: 6px;
}
.field .required { color: var(--danger); }
.field input, .field select {
  width: 100%; padding: 10px 12px;
  border: 1.5px solid var(--border); border-radius: var(--radius-sm);
  font-size: 14px; color: var(--text); background: #fff;
  transition: border-color 0.2s, box-shadow 0.2s; outline: none;
  appearance: none;
}
.field input:focus, .field select:focus {
  border-color: var(--primary); box-shadow: 0 0 0 3px rgba(79,70,229,.1);
}
.field input::placeholder { color: var(--text-light); }
.field select { background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 20 20' fill='%2394a3b8'%3E%3Cpath fill-rule='evenodd' d='M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z' clip-rule='evenodd'/%3E%3C/svg%3E"); background-repeat: no-repeat; background-position: right 10px center; background-size: 16px; padding-right: 36px; }

.error-msg {
  background: var(--danger-light); color: var(--danger);
  border-radius: var(--radius-sm); padding: 12px 14px;
  font-size: 13px; margin-top: 20px;
}

.form-actions {
  display: flex; justify-content: flex-end; gap: 12px; margin-top: 28px;
  padding-top: 20px; border-top: 1px solid var(--border);
}
.btn-cancel {
  padding: 10px 24px; border: 1.5px solid var(--border); border-radius: var(--radius-sm);
  background: #fff; font-size: 14px; color: var(--text-muted); cursor: pointer; transition: all 0.2s;
}
.btn-cancel:hover { border-color: var(--text-muted); color: var(--text); }
.btn-submit {
  padding: 10px 28px; background: var(--primary); color: #fff;
  border: none; border-radius: var(--radius-sm); font-size: 14px; font-weight: 500;
  cursor: pointer; transition: background 0.2s; display: flex; align-items: center; gap: 8px;
}
.btn-submit:hover:not(:disabled) { background: var(--primary-hover); }
.btn-submit:disabled { opacity: 0.65; cursor: not-allowed; }
.spinner {
  width: 14px; height: 14px;
  border: 2px solid rgba(255,255,255,.4); border-top-color: #fff;
  border-radius: 50%; animation: spin 0.7s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }
</style>
