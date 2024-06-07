/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        dark:{
          DEFAULT : "#2A2F4F",
        },
        pink:{
          DEFAULT : "#FDE2F3",
        },
        darkPink:{
          DEFAULT : "#E5BEEC",
        }
      },
    },
  },
  plugins: [],
};
