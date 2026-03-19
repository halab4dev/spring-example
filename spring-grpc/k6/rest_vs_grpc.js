import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  // vus: __ENV.VUS ? parseInt(__ENV.VUS) : 50,
  // duration: __ENV.DURATION || '30s',
  scenarios: {
    benchmark: {
      executor: 'constant-vus',
      vus: __ENV.VUS ? parseInt(__ENV.VUS) : 50,
      duration: __ENV.DURATION || '30s',
    },
  },
};

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8081';
const SIZE_BYTES = __ENV.SIZE_BYTES ? parseInt(__ENV.SIZE_BYTES) : 1024;
const MODE = __ENV.MODE || 'rest'; // 'rest' or 'grpc'

export default function () {
  const id = `k6-${__ITER}`;
  const path = MODE === 'grpc' ? '/gateway/grpc' : '/gateway/rest';
  const url = `${BASE_URL}${path}?id=${id}&sizeBytes=${SIZE_BYTES}`;

  const res = http.get(url, {
    tags: { protocol: MODE }
  });

  check(res, {
    'status is 200': (r) => r.status === 200,
  });

}

