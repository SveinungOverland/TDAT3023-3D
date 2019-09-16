using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class StartForce : MonoBehaviour
{
    public Rigidbody body;
    public Vector3 force = new Vector3(0, 0, 0);
    public float scale = 1;
    // Start is called before the first frame update
    void Start()
    {
        body.AddForce(force * scale, ForceMode.Impulse);
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
