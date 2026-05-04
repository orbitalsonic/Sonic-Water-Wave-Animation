package com.orbitalsonic.waterwave.wave;

final class WaveMath {

    private WaveMath() {
    }

    static float sample(WaveType type, float angle) {
        switch (type) {
            case SINE:
                return (float) Math.sin(angle);
            case SHARP: {
                double s = Math.sin(angle);
                float a = (float) Math.abs(s);
                float sign = (float) Math.copySign(1d, s);
                return sign * (float) Math.pow(a, 0.32d);
            }
            case TRIANGLE:
                return (float) (2d / Math.PI * Math.asin(Math.sin(angle)));
            case NOISE:
            default:
                return noiseComposite(angle);
        }
    }

    private static float noiseComposite(float angle) {
        return (float) (Math.sin(angle) * 0.52d
                + Math.sin(angle * 2.11d + 0.4d) * 0.28d
                + Math.sin(angle * 5.73d - 1.1d) * 0.2d);
    }
}
