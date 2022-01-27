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
  }
})
