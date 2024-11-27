/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        dark: {
          DEFAULT: "#2A2F4F",
        },
        pink: {
          DEFAULT: "#FDE2F3",
        },
        darkPink: {
          DEFAULT: "#E5BEEC",
        },
        gray: {
          DEFAULT: "hsla(0, 0%, 100%, .9)",
        },
        darkGray: {
          DEFAULT: "rgb(39 38 46/var(--tw-text-opacity))",
        },
      },
    },
    backgroundImage: {
      camping: "url('/public/assets/map/camping/camping.png')",
      beach: "url('/public/assets/map/beach/beach.png')",
    },
  },
  plugins: [],
};
