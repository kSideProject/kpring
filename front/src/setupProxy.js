const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = (app) => {
  app.use(
    "/user",
    createProxyMiddleware({
      target: "https://kpring.duckdns.org",
      changeOrigin: true,
    })
  );
};
