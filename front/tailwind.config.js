const colors = require('tailwindcss/colors')

/** @type {import('tailwindcss').Config} */
module.exports = {
    darkMode: 'class',
    theme: {
        extend: {
            colors: {
                primary: {
                    50: '#fdf2f4',
                    100: '#fce7eb',
                    200: '#f8d0d8',
                    300: '#f2aab9',
                    400: '#ea7d94',
                    500: '#de536f',
                    600: '#cc3352',
                    700: '#903749', // Couleur de base
                    800: '#7a2f3e',
                    900: '#672c37',
                },
                secondary: {
                    50: '#f0f9ff',
                    100: '#e0f2fe',
                    200: '#b9e6fe',
                    300: '#7cd4fd',
                    400: '#36bffa',
                    500: '#0ca5e9',
                    600: '#0284c7',
                    700: '#0369a1',
                    800: '#075985',
                    900: '#0c4a6e',
                },
                neutral: {
                    50: '#f8fafc',
                    100: '#f1f5f9',
                    200: '#e2e8f0',
                    300: '#cbd5e1',
                    400: '#94a3b8',
                    500: '#64748b',
                    600: '#475569',
                    700: '#334155',
                    800: '#1e293b',
                    900: '#0f172a',
                },
            },
        },
    },
    plugins: [
        function({ addComponents, theme }) {
            addComponents({
                '.btn': {
                    padding: '.5rem 1rem',
                    borderRadius: '.25rem',
                    fontWeight: '600',
                    transition: 'background-color 0.3s ease',
                },
                '.btn-primary': {
                    backgroundColor: theme('colors.secondary.DEFAULT'),
                    color: theme('colors.text.dark'),
                    '&:hover': {
                        backgroundColor: theme('colors.secondary.dark'),
                    },
                },
                '.btn-secondary': {
                    backgroundColor: theme('colors.accent.1.DEFAULT'),
                    color: theme('colors.text.dark'),
                    '&:hover': {
                        backgroundColor: theme('colors.accent.1.dark'),
                    },
                },
                // Ajout de classes pour le thème
                '.theme-light': {
                    backgroundColor: theme('colors.background.light'),
                    color: theme('colors.text.light'),
                },
                '.theme-dark': {
                    backgroundColor: theme('colors.background.dark'),
                    color: theme('colors.text.dark'),
                },
            })
        }
    ],
}