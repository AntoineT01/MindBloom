// ~/services/authService.ts

interface LoginData {
    email: string;
    password: string;
}

interface LoginResponse {
    token?: string;
    message?: string;
    // Ajoutez d'autres champs si nécessaire selon la réponse de votre API
}

export async function loginUser(data: LoginData): Promise<LoginResponse> {
    try {
        console.log('Tentative de connexion avec:', data);

        // Utiliser directement /api/login qui sera géré par la règle de rewrite Vercel
        const url = '/api/login';

        console.log('URL de l\'API:', url);

        // Effectuer la requête
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: data.email,
                password: data.password
            }),
        });

        console.log('Response status:', response.status);

        // Vérifier si la réponse a du contenu
        const contentType = response.headers.get('content-type');
        const hasContent = contentType && contentType.includes('application/json');

        if (!response.ok) {
            if (hasContent) {
                try {
                    const errorData = await response.json();
                    throw new Error(errorData.message || 'Échec de la connexion');
                } catch (jsonError) {
                    throw new Error(`Erreur ${response.status}: ${response.statusText}`);
                }
            } else {
                throw new Error(`Erreur ${response.status}: ${response.statusText}`);
            }
        }

        if (!hasContent) {
            console.log('La réponse ne contient pas de JSON valide');
            return { message: 'Connexion réussie' }; // Réponse par défaut si pas de JSON
        }

        try {
            const responseData = await response.json();
            console.log('Login successful:', responseData);

            // Si votre API renvoie un token, vous pouvez le stocker dans le localStorage
            if (responseData.token) {
                localStorage.setItem('auth_token', responseData.token);
            }

            return responseData;
        } catch (jsonError) {
            console.error('Erreur lors du parsing JSON:', jsonError);
            return { message: 'Connexion réussie' }; // Fallback en cas d'erreur de parsing
        }
    } catch (error) {
        console.error('Login error in service:', error);
        if (error instanceof Error) {
            throw error;
        }
        throw new Error('Erreur lors de la connexion');
    }
}

// Fonction pour vérifier si l'utilisateur est connecté
export function isUserLoggedIn(): boolean {
    return !!localStorage.getItem('auth_token');
}

// Fonction pour déconnecter l'utilisateur
export function logoutUser(): void {
    localStorage.removeItem('auth_token');
}

// Fonction pour obtenir le token d'authentification
export function getAuthToken(): string | null {
    return localStorage.getItem('auth_token');
}