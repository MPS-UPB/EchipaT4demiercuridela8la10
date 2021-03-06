package ro.mps.screen.api;

import ro.mps.screen.base.Node;

/**
 * Interface implemented by nodes that sit witin a Copound node e.g. Lines within Blocks
 *
 * @author radu
 */
public interface HasParent<T extends Node> {

    Compound<T> getParent();

    void setParent(Compound<T> p);

}
