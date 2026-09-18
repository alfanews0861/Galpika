import { defineConfig } from 'vite';
import path from 'path';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// https://vitejs.dev/config/
export default defineConfig({
  resolve: {
    alias: [
      {
        find: /^lucide-react$/,
        replacement: path.resolve(__dirname, './src/components/icons.js'),
      },
    ],
  },
  plugins: [
    {
      name: 'node24-transform-compat',
      transform(code, id) {
        // Pass-through hook ensures smooth JS event-loop scheduling in Node 24
        return null;
      },
    },
  ],
  esbuild: {
    jsx: 'automatic',
  },
  server: {
    port: 3000,
    open: false,
  },
});
