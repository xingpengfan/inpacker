package inpacker.px

import inpacker.core.PackConfig

class PxPackConfig implements PackConfig<PxPackItem> {
    @Override
    boolean test(PxPackItem item) {
        throw new UnsupportedOperationException("not implemented")
    }

    @Override
    String getUniqueId() {
        throw new UnsupportedOperationException("not implemented")
    }

    @Override
    int numberOfItems() {
        throw new UnsupportedOperationException("not implemented")
    }
}
