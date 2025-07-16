import '@testing-library/jest-dom';
import crypto from 'crypto';

// Mock crypto for jose in JSDOM
Object.defineProperty(global.self, 'crypto', {
  value: {
    getRandomValues: (arr: Uint8Array) => crypto.randomFillSync(arr),
  },
});
