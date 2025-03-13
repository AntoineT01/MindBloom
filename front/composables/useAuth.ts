import { ref, computed, onMounted } from 'vue'
import { logoutUser as logoutUserService, getUserData, isUserLoggedIn, loginUser as loginUserService } from '~/services/authService'

export const useAuth = () => {
    const token = useCookie('auth_token')
    const user = ref(null)
    const loading = ref(false)
    const error = ref(null)
    const isAuthenticated = ref(false)

    // Vérifier si l'utilisateur est authentifié
    const checkAuth = () => {
        if (process.client) {
            isAuthenticated.value = !!token.value || isUserLoggedIn()
            if (isUserLoggedIn()) {
                user.value = getUserData()
            }
        }
    }

    // Initialiser l'état d'authentification au montage
    onMounted(() => {
        checkAuth()
    })

    const login = async (email: string, password: string) => {
        loading.value = true
        error.value = null
        try {
            const response = await loginUserService({ email, password })
            token.value = response.token
            user.value = response.user
            isAuthenticated.value = true
            checkAuth() // Mettre à jour l'état d'authentification
            return true
        } catch (e) {
            error.value = e.message
            return false
        } finally {
            loading.value = false
        }
    }

    const logout = async () => {
        logoutUserService()
        token.value = null
        user.value = null
        isAuthenticated.value = false
        await navigateTo('/')
    }

    return {
        login,
        logout,
        user,
        loading,
        error,
        isAuthenticated,
        checkAuth
    }
}