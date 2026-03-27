<template>
  <div class="page">
    <!-- 顶部导航 -->
    <header class="topbar">
      <div class="topbar-left">
        <svg width="28" height="28" viewBox="0 0 36 36" fill="none">
          <rect width="36" height="36" rx="10" fill="#4f46e5"/>
          <path d="M10 24L18 12L26 24H10Z" fill="white" opacity="0.9"/>
          <circle cx="18" cy="22" r="4" fill="white"/>
        </svg>
        <span class="site-name">学生管理系统</span>
      </div>
      <div class="topbar-right">
        <button class="btn-logout" @click="logout">
          <svg viewBox="0 0 20 20" fill="currentColor">
            <path fill-rule="evenodd" d="M3 3a1 1 0 011 1v12a1 1 0 11-2 0V4a1 1 0 011-1zm7.707 3.293a1 1 0 010 1.414L9.414 9H17a1 1 0 110 2H9.414l1.293 1.293a1 1 0 01-1.414 1.414l-3-3a1 1 0 010-1.414l3-3a1 1 0 011.414 0z" clip-rule="evenodd"/>
          </svg>
          退出登录
        </button>
      </div>
    </header>

    <main class="main">
      <!-- 页面标题 + 新增按钮 -->
      <div class="page-header">
        <div>
          <h1>学生列表</h1>
          <p class="page-desc">共 <strong>{{ total }}</strong> 名学生</p>
        </div>
        <button class="btn-add" @click="goAdd">
          <svg viewBox="0 0 20 20" fill="currentColor">
            <path fill-rule="evenodd" d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z" clip-rule="evenodd"/>
          </svg>
          新增学生
        </button>
      </div>

      <!-- 搜索栏 -->
      <div class="search-bar">
        <div class="search-field">
          <svg class="search-icon" viewBox="0 0 20 20" fill="currentColor">
            <path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"/>
          </svg>
          <input v-model="search.name" placeholder="搜索姓名..." @keyup.enter="doSearch" />
        </div>
        <div class="search-field">
          <svg class="search-icon" viewBox="0 0 20 20" fill="currentColor">
            <path d="M10.394 2.08a1 1 0 00-.788 0l-7 3a1 1 0 000 1.84L5.25 8.051a.999.999 0 01.356-.257l4-1.714a1 1 0 11.788 1.838L7.667 9.088l1.94.831a1 1 0 00.787 0l7-3a1 1 0 000-1.838l-7-3zM3.31 9.397L5 10.12v4.102a8.969 8.969 0 00-1.05-.174 1 1 0 01-.89-.89 11.115 11.115 0 01.25-3.762zM9.3 16.573A9.026 9.026 0 007 14.935v-3.957l1.818.78a3 3 0 002.364 0l5.508-2.361a11.026 11.026 0 01.25 3.762 1 1 0 01-.89.89 8.968 8.968 0 00-5.35 2.524 1 1 0 01-1.4 0zM6 18a1 1 0 001-1v-2.065a8.935 8.935 0 00-2-.712V17a1 1 0 001 1z"/>
          </svg>
          <input v-model="search.major" placeholder="搜索专业..." @keyup.enter="doSearch" />
        </div>
        <button class="btn-search" @click="doSearch">搜索</button>
        <button class="btn-reset" @click="resetSearch">重置</button>
      </div>

      <!-- 表格 -->
      <div class="card">
        <div v-if="loading" class="loading-state">
          <span class="spinner-dark"></span> 加载中...
        </div>
        <div v-else-if="students.length === 0" class="empty-state">
          <svg viewBox="0 0 64 64" fill="none">
            <circle cx="32" cy="32" r="30" stroke="#e2e8f0" stroke-width="2"/>
            <path d="M20 40c0-6.627 5.373-12 12-12s12 5.373 12 12" stroke="#cbd5e1" stroke-width="2" stroke-linecap="round"/>
            <circle cx="32" cy="24" r="6" stroke="#cbd5e1" stroke-width="2"/>
          </svg>
          <p>暂无学生数据</p>
        </div>
        <table v-else>
          <thead>
            <tr>
              <th>学号</th>
              <th>姓名</th>
              <th>性别</th>
              <th>年龄</th>
              <th>专业</th>
              <th>邮箱</th>
              <th>手机</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="s in students" :key="s.id">
              <td><span class="student-no">{{ s.studentNo }}</span></td>
              <td><span class="name-badge">{{ s.name }}</span></td>
              <td>
                <span class="gender-tag" :class="s.gender === '男' ? 'male' : 'female'">
                  {{ s.gender || '-' }}
                </span>
              </td>
              <td>{{ s.age || '-' }}</td>
              <td>{{ s.major || '-' }}</td>
              <td class="text-muted">{{ s.email || '-' }}</td>
              <td class="text-muted">{{ s.phone || '-' }}</td>
              <td>
                <span class="status-tag" :class="s.enabled ? 'enabled' : 'disabled'">
                  {{ s.enabled ? '启用' : '禁用' }}
                </span>
                <div class="action-btns">
                  <button v-if="!s.enabled" class="btn-enable" @click="enableStudent(s.id)">启用</button>
                  <button v-else class="btn-disable" @click="disableStudent(s.id)">禁用</button>
                  <button class="btn-edit" @click="goEdit(s.id)">编辑</button>
                  <button class="btn-delete" @click="deleteStudent(s.id)">删除</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- 分页 -->
      <div class="pagination" v-if="totalPages > 1">
        <button :disabled="page <= 1" @click="changePage(page - 1)" class="page-btn">
          <svg viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clip-rule="evenodd"/></svg>
        </button>
        <button
          v-for="p in pageRange"
          :key="p"
          class="page-btn"
          :class="{ active: p === page, ellipsis: p === '...' }"
          :disabled="p === '...'"
          @click="p !== '...' && changePage(p)"
        >{{ p }}</button>
        <button :disabled="page >= totalPages" @click="changePage(page + 1)" class="page-btn">
          <svg viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd"/></svg>
        </button>
        <span class="page-info">第 {{ page }} / {{ totalPages }} 页</span>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { studentAPI, authAPI } from '../api'

const router = useRouter()
const students = ref([])
const total = ref(0)
const page = ref(1)
const pageSize = 10
const loading = ref(false)
const search = reactive({ name: '', major: '' })

const totalPages = computed(() => Math.max(1, Math.ceil(total.value / pageSize)))

const pageRange = computed(() => {
  const total = totalPages.value
  const cur = page.value
  if (total <= 7) return Array.from({ length: total }, (_, i) => i + 1)
  const pages = []
  if (cur <= 4) {
    for (let i = 1; i <= 5; i++) pages.push(i)
    pages.push('...', total)
  } else if (cur >= total - 3) {
    pages.push(1, '...')
    for (let i = total - 4; i <= total; i++) pages.push(i)
  } else {
    pages.push(1, '...', cur - 1, cur, cur + 1, '...', total)
  }
  return pages
})

const loadStudents = async () => {
  loading.value = true
  try {
    const res = await studentAPI.getList({ ...search, page: page.value, pageSize })
    students.value = res.data.list
    total.value = res.data.total
  } catch (err) {
    console.error(err)
  } finally {
    loading.value = false
  }
}

const doSearch = () => { page.value = 1; loadStudents() }
const resetSearch = () => { search.name = ''; search.major = ''; doSearch() }
const changePage = (p) => { page.value = p; loadStudents() }
const goAdd = () => router.push('/students/add')
const goEdit = (id) => router.push(`/students/${id}/edit`)

const deleteStudent = async (id) => {
  if (!confirm('确认删除该学生？此操作不可撤销。')) return
  try {
    await studentAPI.delete(id)
    loadStudents()
  } catch {
    alert('删除失败')
  }
}

const enableStudent = async (id) => {
  try {
    await studentAPI.enable(id)
    loadStudents()
  } catch {
    alert('启用失败')
  }
}

const disableStudent = async (id) => {
  if (!confirm('确认禁用该学生？')) return
  try {
    await studentAPI.disable(id)
    loadStudents()
  } catch {
    alert('禁用失败')
  }
}

const logout = async () => {
  try { await authAPI.logout() } catch {}
  localStorage.removeItem('token')
  router.push('/login')
}

onMounted(loadStudents)
</script>

<style scoped>
.page { min-height: 100vh; background: var(--bg); }

/* Topbar */
.topbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 32px;
  height: 60px;
  background: #fff;
  border-bottom: 1px solid var(--border);
  box-shadow: var(--shadow-sm);
  position: sticky;
  top: 0;
  z-index: 100;
}
.topbar-left { display: flex; align-items: center; gap: 10px; }
.site-name { font-size: 15px; font-weight: 600; color: var(--text); }
.btn-logout {
  display: flex; align-items: center; gap: 6px;
  padding: 7px 14px; border: 1.5px solid var(--border);
  border-radius: var(--radius-sm); background: #fff;
  font-size: 13px; color: var(--text-muted); cursor: pointer;
  transition: all 0.2s;
}
.btn-logout svg { width: 15px; height: 15px; }
.btn-logout:hover { border-color: var(--danger); color: var(--danger); background: var(--danger-light); }

/* Main */
.main { max-width: 1200px; margin: 0 auto; padding: 32px 24px; }

/* Page header */
.page-header {
  display: flex; align-items: flex-start; justify-content: space-between;
  margin-bottom: 24px;
}
.page-header h1 { font-size: 20px; font-weight: 700; color: var(--text); margin-bottom: 2px; }
.page-desc { font-size: 13px; color: var(--text-muted); }
.page-desc strong { color: var(--primary); }
.btn-add {
  display: flex; align-items: center; gap: 6px;
  padding: 9px 18px; background: var(--primary); color: #fff;
  border: none; border-radius: var(--radius-sm); font-size: 14px; font-weight: 500;
  cursor: pointer; transition: background 0.2s, transform 0.1s;
}
.btn-add svg { width: 16px; height: 16px; }
.btn-add:hover { background: var(--primary-hover); }
.btn-add:active { transform: scale(0.98); }

/* Search */
.search-bar {
  display: flex; gap: 10px; align-items: center;
  margin-bottom: 20px; flex-wrap: wrap;
}
.search-field {
  position: relative; flex: 1; min-width: 180px; max-width: 260px;
}
.search-icon {
  position: absolute; left: 10px; top: 50%; transform: translateY(-50%);
  width: 15px; height: 15px; color: var(--text-light); pointer-events: none;
}
.search-field input {
  width: 100%; padding: 9px 12px 9px 32px;
  border: 1.5px solid var(--border); border-radius: var(--radius-sm);
  font-size: 13px; color: var(--text); background: #fff;
  transition: border-color 0.2s, box-shadow 0.2s; outline: none;
}
.search-field input:focus {
  border-color: var(--primary); box-shadow: 0 0 0 3px rgba(79,70,229,.1);
}
.search-field input::placeholder { color: var(--text-light); }
.btn-search {
  padding: 9px 20px; background: var(--primary); color: #fff;
  border: none; border-radius: var(--radius-sm); font-size: 13px; font-weight: 500;
  cursor: pointer; transition: background 0.2s;
}
.btn-search:hover { background: var(--primary-hover); }
.btn-reset {
  padding: 9px 16px; background: #fff; color: var(--text-muted);
  border: 1.5px solid var(--border); border-radius: var(--radius-sm);
  font-size: 13px; cursor: pointer; transition: all 0.2s;
}
.btn-reset:hover { border-color: var(--text-muted); color: var(--text); }

/* Card / Table */
.card {
  background: #fff;
  border-radius: var(--radius);
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border);
  overflow: hidden;
}

.loading-state, .empty-state {
  display: flex; flex-direction: column;
  align-items: center; justify-content: center;
  padding: 60px 20px; color: var(--text-muted); font-size: 14px; gap: 12px;
}
.empty-state svg { width: 64px; height: 64px; }
.spinner-dark {
  display: inline-block; width: 18px; height: 18px;
  border: 2px solid var(--border); border-top-color: var(--primary);
  border-radius: 50%; animation: spin 0.7s linear infinite;
}
@keyframes spin { to { transform: rotate(360deg); } }

table {
  width: 100%; border-collapse: collapse;
}
thead tr {
  background: #f8fafc;
  border-bottom: 1px solid var(--border);
}
th {
  padding: 12px 16px;
  font-size: 12px; font-weight: 600;
  color: var(--text-muted); text-transform: uppercase; letter-spacing: .5px;
  text-align: left; white-space: nowrap;
}
tbody tr {
  border-bottom: 1px solid #f1f5f9;
  transition: background 0.15s;
}
tbody tr:last-child { border-bottom: none; }
tbody tr:hover { background: #fafbff; }
td {
  padding: 13px 16px; font-size: 14px; color: var(--text);
  vertical-align: middle;
}
.text-muted { color: var(--text-muted); font-size: 13px; }

.student-no {
  font-family: ui-monospace, monospace; font-size: 12px;
  background: var(--primary-light); color: var(--primary);
  padding: 2px 8px; border-radius: 4px;
}
.name-badge { font-weight: 500; }

.gender-tag {
  display: inline-flex; align-items: center;
  padding: 2px 10px; border-radius: 20px; font-size: 12px; font-weight: 500;
}
.gender-tag.male { background: #eff6ff; color: #3b82f6; }
.gender-tag.female { background: #fdf2f8; color: #ec4899; }

.status-tag {
  display: inline-flex; align-items: center;
  padding: 2px 10px; border-radius: 20px; font-size: 12px; font-weight: 500;
  margin-right: 8px;
}
.status-tag.enabled { background: #ecfdf5; color: #059669; }
.status-tag.disabled { background: #fef2f2; color: #dc2626; }

.btn-enable {
  padding: 5px 12px; font-size: 12px; font-weight: 500;
  border: 1.5px solid #059669; color: #059669;
  background: #fff; border-radius: var(--radius-sm); cursor: pointer;
  transition: all 0.2s;
}
.btn-enable:hover { background: #ecfdf5; }
.btn-disable {
  padding: 5px 12px; font-size: 12px; font-weight: 500;
  border: 1.5px solid #dc2626; color: #dc2626;
  background: #fff; border-radius: var(--radius-sm); cursor: pointer;
  transition: all 0.2s;
}
.btn-disable:hover { background: #fef2f2; }

.action-btns { display: flex; gap: 8px; }
.btn-edit {
  padding: 5px 12px; font-size: 12px; font-weight: 500;
  border: 1.5px solid var(--primary); color: var(--primary);
  background: #fff; border-radius: var(--radius-sm); cursor: pointer;
  transition: all 0.2s;
}
.btn-edit:hover { background: var(--primary-light); }
.btn-delete {
  padding: 5px 12px; font-size: 12px; font-weight: 500;
  border: 1.5px solid var(--border); color: var(--text-muted);
  background: #fff; border-radius: var(--radius-sm); cursor: pointer;
  transition: all 0.2s;
}
.btn-delete:hover { border-color: var(--danger); color: var(--danger); background: var(--danger-light); }

/* Pagination */
.pagination {
  display: flex; align-items: center; gap: 6px; margin-top: 20px; flex-wrap: wrap;
}
.page-btn {
  display: inline-flex; align-items: center; justify-content: center;
  min-width: 34px; height: 34px; padding: 0 6px;
  border: 1.5px solid var(--border); border-radius: var(--radius-sm);
  background: #fff; font-size: 13px; color: var(--text-muted);
  cursor: pointer; transition: all 0.2s;
}
.page-btn svg { width: 14px; height: 14px; }
.page-btn:hover:not(:disabled):not(.ellipsis) {
  border-color: var(--primary); color: var(--primary); background: var(--primary-light);
}
.page-btn.active {
  background: var(--primary); border-color: var(--primary); color: #fff;
}
.page-btn:disabled { opacity: 0.4; cursor: not-allowed; }
.page-btn.ellipsis { border: none; background: none; cursor: default; }
.page-info { font-size: 13px; color: var(--text-muted); margin-left: 8px; }
</style>
