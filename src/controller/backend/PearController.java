package controller.backend;

import model.exceptions.ReferenceNotFoundException;
import model.exceptions.NotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import model.exceptions.CollectionIsEmptyException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import model.Pear;
import model.exceptions.NotExistPearOnlineException;

/**
 *
 * @author Uellington Conceição
 */
public class PearController {

    private Pear reference;
    private Pear thisPear;
    private Queue<Pear> allPears;

    public PearController() {
        this.allPears = new PriorityQueue();
    }

    public void setKnownPears(Queue<Pear> pears) {
        this.allPears = pears;
    }

    public Collection<Pear> getKnownPears() {
        return Collections.unmodifiableCollection(this.allPears);
    }

    public boolean thisPearIsReference() {
        return this.thisPear.isReference();
    }

    public boolean isReference(String ip, int port) {
        System.out.println("IP: " + ip + " Port: " + port);
        System.out.println("Reference: " + this.reference.getID());
        return this.reference.getID().equals(ip + port);
    }

    public Pear getReference() throws ReferenceNotFoundException {
        return this.allPears.stream()
                .filter(Pear::isReference)
                .findAny()
                .orElseThrow(ReferenceNotFoundException::new);
    }

    public void setReference(String ip, int port) {
        this.setReference(new Pear(ip, port, true, true));
    }

    public void setReference(Pear pear) {
        this.reference = pear;
    }

    public void setThisPear(String clientIP, int clientPort) {
        this.setThisPear(new Pear(clientIP, clientPort, true, false));
    }

    public void setThisPear(Pear pear) {
        this.thisPear = pear;
    }

    public void addNewPear(String ip, int port, boolean status, boolean reference) {
        this.addNewPear(new Pear(ip, port, status, reference));
    }

    public void addNewPear(String ip, int port, boolean status) {
        this.addNewPear(new Pear(ip, port, status, false));
    }

    public void addNewPear(String ip, int port) {
        this.addNewPear(new Pear(ip, port, true, false));
    }

    public void addNewPear(Pear pear) {
        this.allPears.add(pear);
    }

    public void updateStatePear(String id, boolean status) {
        this.allPears.stream()
                .filter(current -> current.getID().equals(id))
                .findFirst()
                .ifPresent(pear -> pear.setStatus(status));
    }

    public Pear getFirstPear() throws CollectionIsEmptyException {
        return this.allPears.stream()
                .findFirst()
                .orElseThrow(CollectionIsEmptyException::new);
    }

    public Pear getFristPearOnline() throws NotExistPearOnlineException {
        return this.allPears.stream()
                .sorted()
                .filter(Pear::isOnline)
                .findFirst()
                .orElseThrow(NotExistPearOnlineException::new);
    }

    public List<Pear> getAllDisconnectedPears() {
        return this.allPears.stream()
                .collect(Collectors.partitioningBy(Pear::isOnline))
                .get(false);
    }

    public List<Pear> getAllConnectedPears() {
        return this.allPears.stream()
                .collect(Collectors.partitioningBy(Pear::isOnline))
                .get(true);
    }

    public Pear getPear(String id) throws NotFoundException {
        return this.allPears.stream()
                .filter(pear -> pear.getID().equals(id))
                .findAny()
                .orElseThrow(NotFoundException::new);
    }

    public void verifyReference() throws ReferenceNotFoundException {
    }
}
