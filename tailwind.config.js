/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        heading: ['Ramabhadra', 'sans-serif'],
        body: ['Mallanna', 'sans-serif'],
      },
      colors: {
        brand: {
          50: '#eff6ff',
          100: '#dbeafe',
          200: '#bfdbfe',
          300: '#93c5fd',
          400: '#60a5fa',
          500: '#3b82f6',
          600: '#2563eb', // Primary Blue
          700: '#1d4ed8',
          800: '#1e40af', // Navy Dark Blue
          900: '#1e3a8a',
          950: '#172554',
        },
        satire: {
          accent: '#f59e0b', // Amber highlight for satire punchlines
          tag: '#e0e7ff',
          dark: '#0f172a',
        }
      }
    },
  },
  plugins: [],
}
