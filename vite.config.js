import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

export default defineConfig({
    //root: './src/main/webapp',
    plugins: [vue()],
    build: {
        outDir: './src/main/webapp/dist',
        emptyOutDir: true,
        rollupOptions: {
            output: {
                entryFileNames: '[name].js',
                chunkFileNames: '[name].js',
                assetFileNames: '[name].[ext]'
            }
        }
    },
    //configure server
    server: {
        port: 8081, // local port
        open: true, // automatically open browser when server starts
        proxy: { //proxy config
            '/api': 'http://localhost:8080', // proxy all request starting with /api to target
            '/logout': 'http://localhost:8080', // proxy all request starting with /api to target
        }
    }
});