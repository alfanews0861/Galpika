const { build } = require('vite');

async function run() {
  try {
    await build({
      configFile: 'vite.config.js',
      logLevel: 'warn'
    });
    console.log('Galpika build completed successfully!');
  } catch (err) {
    console.error('Build error:', err);
    process.exit(1);
  }
}

run();
