/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: "class",
  theme: {
    extend: {
      fontFamily: {
        orbitron: ["Orbitron", "sans-serif"],
      },
      colors: {
        // Couleurs de base pour les boutons
        "faux-blanc": "#F4F4F4",
        blanc: "#FFFFFF",
        vert: "#8BC34A",
        violet: {
          fonc: "#2E073F", // violet foncé (pour delete)
          defaut: "#7A1CAC", // violet defaut
          clair: "#AD49E1", // violet clair (pour link-1)
          ple: "#EBD3F8", // violet pâle (pour link-2)
        },
        Rouge: {
          dark: "#740938",
          light: "#CC2B52",
          normal: "#AF1740",
        },
        Or: "#FFD700",
        Bronze: "#CE8946",
        Argent: "#C0C0C0",
        Pink: "#DE7C7D",

        // Couleurs thème originales conservées
        primary: {
          50: "#fdf2f4",
          100: "#fce7eb",
          200: "#f8d0d8",
          300: "#f2aab9",
          400: "#ea7d94",
          500: "#de536f",
          600: "#cc3352",
          700: "#903749",
          800: "#7a2f3e",
          900: "#672c37",
        },
        secondary: {
          50: "#f0f9ff",
          100: "#e0f2fe",
          200: "#b9e6fe",
          300: "#7cd4fd",
          400: "#36bffa",
          500: "#0ca5e9",
          600: "#0284c7",
          700: "#0369a1",
          800: "#075985",
          900: "#0c4a6e",
        },
        neutral: {
          50: "#f8fafc",
          100: "#f1f5f9",
          200: "#e2e8f0",
          300: "#cbd5e1",
          400: "#94a3b8",
          500: "#64748b",
          600: "#475569",
          700: "#334155",
          800: "#1e293b",
          900: "#0f172a",
        },
      },
    },
  },
  plugins: [
    function ({ addComponents, theme }) {
      addComponents({
        // Styles de base pour tous les boutons
        ".button": {
          borderRadius: "90px",
          height: "50px",
          position: "relative",
          width: "200px",
        },
        // Styles pour le texte des boutons
        ".button-text": {
          fontFamily: "Orbitron-Regular, Helvetica",
          fontSize: "16px",
          fontWeight: "400",
          letterSpacing: "0",
          lineHeight: "normal",
          textAlign: "center",
          width: "100%",
        },
        // Ombres
        ".button-shadow": {
          boxShadow: "0px 4px 4px rgba(0, 0, 0, 0.25)",
        },
        ".button-shadow-inset": {
          boxShadow: "inset 0px 4px 4px rgba(0, 0, 0, 0.25)",
        },
      });
    },
  ],
};
