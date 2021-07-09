module.exports = (api) => {
  const plugins = {
    autoprefixer: {}
  }
  if (api.mode === 'production') {
    plugins.cssnano = {}
  }

  return {
    plugins
  }
}
