// composables/useAuth.ts
import { ref } from 'vue'

export const useAuth = () => {
    const token = useCookie('auth_token')
    const user = ref(null)
    const loading = ref(false)
    const error = ref(null)

    const login = async (email: string, password: string) => {
        loading.value = true
        error.value = null

        try {
            const response = await fetch('/api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ email, password }),
            })

            if (!response.ok) {
                switch (response.status) {
                    case 400:
                        throw new Error('Données invalides')
                    case 401:
                        throw new Error('Email ou mot de passe incorrect')
                    case 409:
                        throw new Error('Conflit avec les données existantes')
                    case 500:
                        throw new Error('Erreur serveur')
                    default:
                        throw new Error('Une erreur est survenue')
                }
            }

            const data = await response.json()
            token.value = data.token // Ajustez selon la réponse de votre API
            user.value = data.user // Ajustez selon la réponse de votre API

            return true
        } catch (e) {
            error.value = e.message
            return false
        } finally {
            loading.value = false
        }
    }

    const logout = () => {
        token.value = null
        user.value = null
    }

    return {
        login,
        logout,
        user,
        loading,
        error,
        isAuthenticated: computed(() => !!token.value)
    }
}