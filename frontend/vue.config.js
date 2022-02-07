const { defineConfig } = require('@vue/cli-service')
const path = require("path")

module.exports = defineConfig({
  outputDir: path.resolve(__dirname, "../backend-java/src/main/resources/dist"),
  transpileDependencies: [
    'quasar'
  ],

  pluginOptions: {
    vuetify: {},
    quasar: {
      importStrategy: 'kebab',
      rtlSupport: false
    }
  },
  devServer: {
    https: true,
    port: 8083,
    open: true,
    proxy: {
      '/api/v1': {
        target: 'https://localhost:8443/'
      },
      '/webjars': {
        target: 'https://localhost:8443/'
      },
      '/group-call': {
        target: 'https://localhost:8443/'
      },
      '/upload': {
        target: 'https://localhost:8443/'
      },
    },
    historyApiFallback: true,
    hot: true
  },
  // css: {
  //   requireModuleExtension: false
  // },
})
