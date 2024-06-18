/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js,jsx,ts,tsx}"],
  theme: {
    extend: {
      // 기존 색상에서 확장 위해서 extend에 colors 넣는 방식으로 변경
      colors: {
        dark:{
          DEFAULT : "#2A2F4F",
        },
        pink:{
          DEFAULT : "#FDE2F3",
        },
        darkPink:{
          DEFAULT : "#E5BEEC",
        },
        gray:{
          DEFAULT : "hsla(0, 0%, 100%, .9)"
        },
        darkGray:{ 
          DEFAULT : "rgb(39 38 46/var(--tw-text-opacity))"
        },
      },
    },
  },
  plugins: [],
};
