/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: "#5d2fd6",
        secondary: "#aba8ff", // hover
        tertiary: "#343096", // active
        quaternary: "#d5d4ff",
        white: "#f7f7f7",
        black: "#2f2f2f",
        darkBlack: "#191A1E",
        gray: "#697484",
        lightGray: "#f0f2f5",
        disabled: "#d9d9d9",
        error: "#ff5a48",
        confirm: "#3cf584",
      },

      fontSize: {
        h1: "3.8rem",
        h2: "3rem",
        h3: "2.4rem",
        h4: "1.9rem",
        h5: "1.5rem",
        h6: "1.2rem",
        p: "1rem",
        small: "0.8rem",
      },

      fontWeight: {
        bold: 700,
        regular: 400,
        thin: 300,
      },
    },
    keyframes: {
      "move-gradient": {
        "0%": { backgroundPosition: "10% 50%" },
        "100%": { backgroundPosition: "100% 50%" },
      },
    },
    animation: {
      "move-gradient": "move-gradient 3s infinite alternate",
    },
  },
  plugins: [],
};
