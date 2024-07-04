const { defineConfig } = require('@vue/cli-service')
const Components = require('unplugin-vue-components/webpack')
const AutoImport = require('unplugin-auto-import/webpack')
const { VarletImportResolver } = require('@varlet/import-resolver')
const { resolve } = require('path')
module.exports = defineConfig({
  publicPath: './',
  devServer: {
    port: 8000,
  },
  transpileDependencies: true,
  configureWebpack: {
    resolve: {
      symlinks: false,
      alias: {
        vue: resolve('./node_modules/vue'),
      }
    },
    plugins: [
      Components.default({
        resolvers: [VarletImportResolver()]
      }),
      AutoImport.default({
        resolvers: [VarletImportResolver({ autoImport: true })]
      }),
    ]
  }
})
