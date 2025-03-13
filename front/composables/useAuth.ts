import { ref, onMounted } from 'vue'
import { logoutUser as logoutUserService, getUserData, isUserLoggedIn, loginUser as loginUserService } from '~/services/authService'

export const useAuth = () => {
    const user = ref(null)
    const loading = ref(false)
    const error = ref(null)
    const isAuthenticated = ref(false)

    // Vérifier si l'utilisateur est authentifié
    const checkAuth = () => {
        if (process.client) {
            isAuthenticated.value = isUserLoggedIn();
            if (isUserLoggedIn()) {
                user.value = getUserData();
            }
        }
    };

    // Initialiser l'état d'authentification au montage
    onMounted(() => {
        checkAuth();
    });

    const login = async (email: string, password: string) => {
        loading.value = true;
        error.value = null;

        try {
            const response = await loginUserService({ email, password });

            if (response.success) {
                user.value = response.user;
                isAuthenticated.value = true;
                return true;
            } else {
                throw new Error(response.message || 'Échec de la connexion');
            }
        } catch (e) {
            error.value = e.message;
            return false;
        } finally {
            loading.value = false;
        }
    };

    const logout = async () => {
        logoutUserService();
        user.value = null;
        isAuthenticated.value = false;
        await navigateTo('/');
    };

    return {
        login,
        logout,
        user,
        loading,
        error,
        isAuthenticated,
        checkAuth
    };
};